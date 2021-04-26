import React from 'react';
import "../../extra/styles/home-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";
import SearchBar from "../../components/unitSelection/searchBar/searchBar"
import CoursesList from "../../components/unitSelection/coursesList/coursesList"
import Selection from "./Selection"
import { toast } from 'react-toastify';
import API from '../../apis/api';
import './unitSelection-styles.css'

export default class UnitSelection extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            test: 1,
            courses: undefined,
            selections: undefined,
            searchKeyword: null,
            searchFilter: 'All'
        };
        this.updateSelections = this.updateSelections.bind(this);
        this.updateCourses = this.updateCourses.bind(this);
        this.updateSearchFilter = this.updateSearchFilter.bind(this);
        this.initSearchKeyword = this.initSearchKeyword.bind(this);
        this.updateSearchKeyword = this.updateSearchKeyword.bind(this)
    }

    async componentDidMount() {
        document.title = "انتخاب واحد"
        toast.configure({rtl: true, className: "text-center", position: "top-right"})
        this.updateSelections()
        this.updateCourses('')
        this.initSearchKeyword()
    }

    initSearchKeyword() {
        API.get('student/searchKeyword'
        ).then(resp => {
            if(resp.status == 200) {
                this.setState({searchKeyword: resp.data});
                this.updateCourses()
            }
            else{
                toast.error('خطا در انجام عملیات')
            }}).catch(error => {
                console.log(error)
                if(error.response.status == 401 || error.response.status == 403) {
                    window.location.href = "http://localhost:3000/login"
                }
        })
    }

    updateSearchFilter(newSearchFilter) {
        this.setState({searchFilter: newSearchFilter})
        this.updateCourses()
    }

    updateSearchKeyword(newSearchKeyword) {
        this.setState({searchKeyword: newSearchKeyword})
    }

    updateSelections() {
        API.get("offering/selections").then(resp => {
            if(resp.status == 200) {
                this.setState({selections: resp.data});
            }
            else{
                toast.error('خطا در انجام عملیات')
            }}).catch(error => {
                console.log(error)
                if(error.response.status == 401 || error.response.status == 403) {
                    window.location.href = "http://localhost:3000/login"
                }
            })
    }

    updateCourses() {

        API.post('offering/search',
            {
                keyword: this.state.searchKeyword,
                type: this.state.searchFilter
            }
        ).then(resp => {
            if(resp.status == 200) {
                this.setState({courses: resp.data});
            }
            else{
                toast.error('خطا در انجام عملیات')
            }}).catch(error => {
                console.log(error)
                if(error.response.status == 401 || error.response.status == 403) {
                    window.location.href = "http://localhost:3000/login"
                }
            })
    }

    render() {
        return (
            <div className="main-container">
                <Header firstOption={"خانه"}
                        secondOption={"برنامه هفتگی"}
                        firstRoute={""}
                        secondRoute={"/schedule"}/>

                <Selection updateSelections={this.updateSelections} selections = {this.state.selections} />
                <SearchBar updateCourses={this.updateCourses} searchKeyword={this.state.searchKeyword} updateSearchKeyword={this.updateSearchKeyword}/>
                <CoursesList courses={this.state.courses} searchFilter={this.state.searchFilter} updateSearchFilter={this.updateSearchFilter}/>

                <Footer/>
            </div>
        );
    }

}