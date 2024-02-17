import Link from "next/link";

const LoginPage = () => {
    return  <div>
        <div className="bg-white p-40 rounded-xl shadow-md text-center">
            <div className="mb-5">
                <h2>Login</h2>
            </div>
            <form>
                <div className="input-group">
                    <label className="block">Username</label>
                    <input type="text" name="username" className="block w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                </div>
                <div className="input-group">
                    <label className="block">Password</label>
                    <input type="password" name="password" className="block w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                </div>
                <div className="flex justify-center">
                    <button type="submit" className="block px-3 py-1.5 mt-1  relative rounded-xl bg-green-400 text-white border-none cursor-pointer" name="login_user">Login</button>
                </div>
                <p>
                    Not a member yet? <Link href="/register">Sign up</Link>
                </p>
            </form>
      </div>
  </div>
}
export default LoginPage;