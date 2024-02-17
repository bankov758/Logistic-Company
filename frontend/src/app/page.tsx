import React, { Fragment } from "react";

import ClientInterface from "@/components/home/ClientInterface";
import EmployeeInterface from "@/components/home/EmployeeInterface";

const Home: React.FC = () => {
//    const session = await getSession();
 
  return (
		<Fragment>
			{/* <ClientInterface/> */}
			<EmployeeInterface/>
		</Fragment>
	);
}

export default Home;