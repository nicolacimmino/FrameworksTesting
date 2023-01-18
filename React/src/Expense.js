import React from 'react';

class Expense extends React.Component {

    render() {
        return (
            <tr onClick={() => this.props.onSelect(this.value)}>
                <td>
                    {this.props.value.created_at}
                </td>
                <td>
                    {this.props.value.description}
                </td>
                <td>
                    {this.props.value.amount} {this.props.value.currency}
                </td>
            </tr>
        );
    }
}

export default Expense;