import { Component } from "react";
import { CreditCard } from "../models/creditCard";
import { Modal, Form, Button, Alert } from 'react-bootstrap'
import { ProgressBar } from 'react-bootstrap';
import { payBill } from '../actions/card-action'
import { RootState } from '../reducers';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { PayBillState } from '../models/cardState';

interface Props {
    creditCard: CreditCard
    hideModal: () => void
    payBillState: PayBillState
    payBill: (id: string, payload: any) => void
}
interface State {
    amountSelected: number
}
class PayBillModal extends Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            amountSelected: 0
        }
    }
    selectAmount = (amount: number, e: React.ChangeEvent<any>) => {
        this.setState({
            amountSelected: amount
        })
    }
    handleSubmit = (e: React.ChangeEvent<any>) => {
        e.preventDefault()
        const payload = {
            amount: this.state.amountSelected,
        };
        this.props.payBill(this.props.creditCard.cardId, payload)
    }
    render() {
        return (
            <Modal show={true} onHide={this.props.hideModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Pay bill</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form.Check
                        type="radio"
                        label={"Min due: " + this.props.creditCard.minDue}
                        onClick={this.selectAmount.bind(this, this.props.creditCard.minDue)}
                        name="formRadios"
                        id="min_due"
                    />
                    <Form.Check
                        type="radio"
                        label={"Total due: " + this.props.creditCard.totalDue}
                        onClick={this.selectAmount.bind(this, this.props.creditCard.totalDue)}
                        name="formRadios"
                        id="total_due"
                    />
                </Modal.Body>
                {this.props.payBillState.inProgress &&
                    <ProgressBar className="progressbar" animated now={100} />}
                { this.props.payBillState.success ?
                    <Modal.Footer>
                        <Alert variant="success"> Payment success </Alert>
                    </Modal.Footer>
                    :
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.props.hideModal}>
                            Close
                                </Button>
                        <Button variant="primary" disabled={this.state.amountSelected <= 0 ||
                            this.props.payBillState.inProgress}
                            onClick={this.handleSubmit.bind(this)}>
                            Pay
                                </Button>
                    </Modal.Footer>
                }
                {this.props.payBillState.error &&
                    <span className="row d-flex justify-content-center error-message">
                        {this.props.payBillState.error}
                    </span>
                }
            </Modal>
        )
    }
}
const mapStateToProps = (state: RootState) => {
    return { payBillState: state.cardReducer.payBillState };
};
const mapDispatchToProps = (dispatch: any) => {
    return {
        ...bindActionCreators({ payBill }, dispatch)
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(PayBillModal);