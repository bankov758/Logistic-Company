import React, { Fragment } from "react";

import ClientInterface from "@/components/home/ClientInterface";
import EmployeeInterface from "@/components/home/EmployeeInterface";
import AdminInterface from "@/components/home/AdminInterface";

const Home: React.FC = () => {
  return (
		<Fragment>
			<ClientInterface/>
			<EmployeeInterface/>
			<AdminInterface/>
		</Fragment>
	);
}

export default Home;