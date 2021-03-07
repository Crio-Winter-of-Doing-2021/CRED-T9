import { Component } from "react";
import { Container, Col, Row } from "react-bootstrap";
import { Navbar, NavbarBrand, Button, Nav } from 'react-bootstrap';
import { connect } from "react-redux";
import { bindActionCreators } from 'redux';
import { GetAllCardsState } from "../models/cardState";
import { CreditCard } from "../models/creditCard";
import { RootState } from '../reducers';
import CardView from './CardView'
import { getAllCards } from '../actions/card-action'
import '../styles/cards.css'

interface ShowCardsProp {
    getAllCardsState: GetAllCardsState,
    getAllCards: () => void
}

class ShowCards extends Component<ShowCardsProp> {
    componentDidMount() {
        this.props.getAllCards()
    }
    render() {
        let cards = this.props.getAllCardsState.cards
        let cardGrid = cards.reduce(function (result, _, index, array) {
            if (index % 2 === 0) result.push(array.slice(index, index + 2));
            return result;
        }, new Array<Array<CreditCard>>());
        let cardsView = cardGrid.map(items => {
            let rows = []
            let cols = items.map(card => {
                return (<Col className="card-column">{CardView(card)}</Col>)
            })
            rows.push(<Row style={{ marginTop: '36px' }}>{cols}</Row>)
            return rows
        })
        return (
            <div>
                <Navbar expand="lg" sticky="top" variant="light">
                    <NavbarBrand href="/">CRED</NavbarBrand>
                    <Nav className="ml-auto">
                        <Button className="add-card-button" variant="outline-primary" href="/add-card">
                            Add a card
                        </Button>
                        <Button variant="outline-danger">Log out</Button>
                    </Nav>
                </Navbar>
                {this.props.getAllCardsState.cards.length > 0 ? <Container className="cards-container">
                    {cardsView}
                </Container> : (<div className="no-cards_available">No cards available</div>)}
            </div>
        )
    }
}
const mapStateToProps = (state: RootState) => {
    return { getAllCardsState: state.cardReducer.getAllCardsState };
};
const mapDispatchToProps = (dispatch: any) => {
    return {
        ...bindActionCreators({ getAllCards }, dispatch)
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(ShowCards)