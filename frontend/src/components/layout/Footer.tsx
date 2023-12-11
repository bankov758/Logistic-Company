import React from "react";

const Footer: React.FC = () => {

    return (
        <div className="footer">
             <div className="sb_footer section_padding">
                 <div className="sb_footer-links">
                     <div className="sb_footer-links-div">
                         <h4>More info:</h4>
                    </div>
                    <div className="sb_footer-links-div">
                         <a href="/aboutus">
                             <p>About us</p>
                         </a>
                    </div>
                    <div className="sb_footer-links-div">
                         <a href="/contactus">
                             <p>Contact us</p>
                         </a>
                     </div>
                  </div>
             </div>
        </div>

    )
}

export default Footer;