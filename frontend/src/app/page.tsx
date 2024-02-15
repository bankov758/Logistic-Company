import ClientInterface from "@/components/home/ClientInterface";
export default async function Home() {
//    const session = await getSession();
 
  return (
		<section className="flex flex-col justify-start items-start w-full">
			<ClientInterface/>
		</section>
	);
}