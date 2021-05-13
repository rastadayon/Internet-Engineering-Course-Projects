import React from 'react'
import styles from './searchBar-styles.css'
import {toast} from "react-toastify";

class SearchBar extends React.Component {

    constructor(props) {
        super(props)
        this.handleSearchKeywordChange = this.handleSearchKeywordChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleSearchKeywordChange(event) {
        this.props.updateSearchKeyword(event.target.value)
    }


    handleSubmit(event) {
        event.preventDefault();
        this.props.updateCourses(this.props.searchKeyword)
    }


    render() {
        return (
            <div className="search-bar" onSubmit={this.handleSubmit}>
                <div className="outer-box">
                    <form action="" className="w-100 m-0 d-flex justify-content-center align-items-center" onSubmit={this.handleSubmit} >
                        <div className="row w-100 no-gutters">
                            <div className="col-8 col-md m-1">
                                <input type="text" className="search-item" placeholder="نام درس"
                                onChange={this.handleSearchKeywordChange} value={this.props.searchKeyword}/>
                            </div>
                            <div className="search-btn col-4 m-1">
                                <button className="search-item" type="submit">
                                    <span className="search-text">
                                        جستجو
                                    </span>
                                    <span>
                                        <i className="loupe flaticon-loupe"></i>
                                    </span>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}

export default SearchBar