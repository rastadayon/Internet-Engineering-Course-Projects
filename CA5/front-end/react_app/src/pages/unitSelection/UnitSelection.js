import React from 'react';
import "../../extra/styles/home-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";
import SearchBar from "../../components/unitSelection/searchBar/searchBar"
import CoursesList from "../../components/unitSelection/coursesList/coursesList"
import { toast } from 'react-toastify';
import API from '../../apis/api';

export default class UnitSelection extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            test: 1,
            courses: undefined,
            courseSearched: undefined
        };
        // this.updateCourses = this.updateCourses.bind(this);
    }

    async componentDidMount() {
        document.title = "انتخاب واحد";
        toast.configure({rtl: true, className: "text-center", position: "top-right"});
        this.updateCourses('')
    }

    updateCourses(courseSearchedName) {
        var requestParam = new FormData();
        requestParam.append('searchKey', courseSearchedName) 
        API.post('offering/search',
            requestParam
        ).then(resp => {
            console.log('resp.data = ' , resp.data)
            if(resp.status == 200) {
                console.log('search quesry works')
                this.setState({courses : resp.data})
            }
            else{
                toast.error('خطا در انجام عملیات')
            }}).catch(error => {
                if(error.response.status == 401 || error.response.status == 403) {
                    window.location.href = "http://localhost:3030/login"
                }
            })
        }

    render() {
        return (
            <div>
                <Header firstOption={"خانه"}
                        secondOption={"برنامه هفتگی"}
                        firstRoute={""}
                        secondRoute={"/schedule"}/>

                <SearchBar updateCourses={this.updateCourses}/>
                <CoursesList courses={this.state.courses}/>

                <br/>
                <Footer/>
            </div>
        );
    }

}