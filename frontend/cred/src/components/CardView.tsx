import { Card } from "react-bootstrap";
import { CreditCard } from '../models/creditCard'

function CardView(creditCard: CreditCard) {
    return (
        <Card border="dark" style={{ width: '20rem', background: '#BBBBBB' }}>
            <Card.Body>
                <Card.Title>{creditCard.cardNickName}</Card.Title>
                <Card.Text>{creditCard.cardNumber}</Card.Text>
                <Card.Text>{creditCard.nameOnCard}</Card.Text>
                <Card.Link href={"/view-statement/" + creditCard.cardId}>View statement</Card.Link>
                <Card.Link href="/pay-bill">Pay bill</Card.Link>
            </Card.Body>
        </Card>
    )
}

export default CardView