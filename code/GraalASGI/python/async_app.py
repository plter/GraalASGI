import asyncio

async def hello():
    print("Start")
    await asyncio.sleep(3)
    print("End")

asyncio.run(hello())