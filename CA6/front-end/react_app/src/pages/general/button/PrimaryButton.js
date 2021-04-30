import React from "react";
import "./button.css";
import PropTypes from 'prop-types';

export default class PrimaryButton extends React.Component{
    render() {
        return (
            <button type="button" className="btn btn-default btn-primary" disabled={this.props.disabled}>
                {this.props.text}
            </button>
        );
    }
}

PrimaryButton.propTypes = {
    text: PropTypes.string,
    disabled: PropTypes.bool
};