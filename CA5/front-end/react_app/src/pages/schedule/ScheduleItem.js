import * as React from "react";
import "../../assets/styles/courses-styles.css";
import ScheduleIndex from "./ScheduleIndex";

export default class ScheduleItem extends React.Component{

     constructor(props) {
         super(props);
         this.state = {
             indexes : []
         }

         for (var i = 0; i < slots.length; i++) {
             this.state.orders.push( <OrderItem time={slots[i]} /> );
         }
     }


     render() {
         return (
             <div className="orders tab2">
                 {this.state.orders}
             </div>
         );
     }
}